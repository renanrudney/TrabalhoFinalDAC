import { Injectable } from '@angular/core';
import { Voo } from '../models/voo/voo.model';
import { ReservaService } from './reserva.service';
import { Observable, map } from 'rxjs';
import { environment } from '../shared/environment/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class VooService {

  private baseUrl = `${environment.apiGatewayUrl}/voos`;

  constructor(private reservaService: ReservaService, private http: HttpClient) { }

  getVoos(): Observable<Voo[]> {
    return this.http.get<Voo[]>(`${this.baseUrl}`);
  }

  getVoo(codigoVoo: string): Observable<Voo> {
    return this.http.get<Voo>(`${this.baseUrl}/${codigoVoo}`);
  }
  
  getVoosCompra(origem: string, destino: string): Observable<Voo[]> {
    const dataAtual = new Date();
  
    return this.getVoos().pipe(
      map((voos: Voo[]) =>
        voos.filter(
          (voo) =>
            voo.origem === origem &&
            voo.destino === destino &&
            new Date(voo.dataHora) > dataAtual
        )
      )
    );
  }  

  getVoosProximasHoras(): Observable<Voo[]> {
    const agora = new Date();
    const dataLimite = new Date(agora.getTime() + 48 * 60 * 60 * 1000); // Próximas 48 horas
  
    return this.getVoos().pipe(
      map((voos: Voo[]) =>
        voos.filter(
          (voo) =>
            new Date(voo.dataHora) > agora && new Date(voo.dataHora) <= dataLimite
        )
      )
    );
  }

  cancelarVoo(codigoVoo: string): Observable<Voo> {
    return this.http.get<Voo>(`${this.baseUrl}/${codigoVoo}/cancelar`);
  }

  realizarVoo(codigoVoo: string): Observable<Voo> {
    return this.http.get<Voo>(`${this.baseUrl}/${codigoVoo}/realizar`);
  }

  cadastrarVoo(codigoVoo: string, dataHora: Date, origem: string, destino: string, valorPassagem: number, totalPoltronas: number): Observable<Voo> {
    const novoVoo: Voo = new Voo (codigoVoo, dataHora, origem, destino, valorPassagem, totalPoltronas, 0, "CONFIRMADO");
    return this.http.post<Voo>(this.baseUrl, novoVoo);
  }
}

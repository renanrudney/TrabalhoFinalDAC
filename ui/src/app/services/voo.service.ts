import { Injectable } from '@angular/core';
import { Voo } from '../models/voo/voo.model';
import { ReservaService } from './reserva.service';
import { Observable, map } from 'rxjs';
import { environment } from '../shared/environment/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class VooService {

  private baseUrl = `${environment.apiGatewayUrl}/voos`;

  constructor(private reservaService: ReservaService, private http: HttpClient) { }

  getVoos(): Observable<Voo[]> {
    return this.http.get<Voo[]>(`${this.baseUrl}`, { headers: new HttpHeaders({ 'Authorization': "Bearer " + localStorage.getItem('authToken') || '' })});
  }

  getVoo(codigoVoo: string): Observable<Voo> {
    return this.http.get<Voo>(`${this.baseUrl}/${codigoVoo}`, { headers: new HttpHeaders({ 'Authorization': "Bearer " + localStorage.getItem('authToken') || '' })});
  }
  
  getVoosCompra(origem: string, destino: string): Observable<Voo[]> {
    const dataAtual = new Date();
  
    return this.getVoos().pipe(
      map((voos: Voo[]) =>
      {
        console.log(voos)
        console.log(origem)
        console.log(destino)
        return voos.filter(
          (voo) =>
            voo.aeroporto_origem == origem &&
            voo.aeroporto_destino == destino
        )}
      )
    );
  }  

  getVoosProximasHoras(): Observable<Voo[]> {
    const agora = new Date();
    const dataLimite = new Date(agora.getTime() + 48 * 60 * 60 * 1000); // Próximas 48 horas
    return this.getVoos()
    // return this.getVoos().pipe(
    //   map((voos: Voo[]) => {
    //     console.log(voos);
    //     return voos.filter(
    //       (voo) =>
    //         new Date(voo.data) <= dataLimite
    //     )
    //     }
    //   )
    // );
  }

  cancelarVoo(codigoVoo: string): Observable<Voo> {
    return this.http.post<Voo>(`${this.baseUrl}/${codigoVoo}/cancelar`, { headers: new HttpHeaders({ 'Authorization': "Bearer " + localStorage.getItem('authToken') || '' })});
  }

  realizarVoo(codigoVoo: string): Observable<Voo> {
    return this.http.post<Voo>(`${this.baseUrl}/${codigoVoo}/realizar`, { headers: new HttpHeaders({ 'Authorization': "Bearer " + localStorage.getItem('authToken') || '' })});
  }

  cadastrarVoo(codigoVoo: string, dataHora: Date, origem: string, destino: string, valorPassagem: number, totalPoltronas: number): Observable<Voo> {
    const novoVoo: Voo = new Voo (codigoVoo, dataHora, origem, destino, valorPassagem, totalPoltronas, 0, "CONFIRMADO");
    return this.http.post<Voo>(this.baseUrl, novoVoo, { headers: new HttpHeaders({ 'Authorization': "Bearer " + localStorage.getItem('authToken') || '' })});
  }
}

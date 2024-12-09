import { Injectable } from '@angular/core';
import { Aeroporto } from '../models/aeroporto/aeroporto.model';
import { environment } from '../shared/environment/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AeroportoService {

  private baseUrl = `${environment.apiGatewayUrl}/aeroporto`;

  constructor(private http: HttpClient) { }

  getAeroportos(): Observable<Aeroporto[]> {
    return this.http.get<Aeroporto[]>(`${this.baseUrl}`);
  }
}

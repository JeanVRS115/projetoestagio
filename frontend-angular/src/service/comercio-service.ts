import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Comercio } from '@domain/comercio';
import { Observable } from 'rxjs';
import { environment } from '../app/environments/environment';

@Injectable()
export class ComercioService {

  private readonly url =
    `${environment.apiUrl}/comercios`;

  constructor(private http: HttpClient) {}

  pesquisarComercios(): Observable<Comercio[]> {
    return this.http.get<Comercio[]>(this.url);
  }

  excluir(comercio: Comercio): Observable<void> {
    return this.http.delete<void>(
      `${this.url}/${comercio.id}`
    );
  }

  salvar(comercio: Comercio): Observable<void> {
    if (comercio.id !== undefined) {
      return this.http.put<void>(
        `${this.url}/${comercio.id}`,
        comercio
      );
    }

    return this.http.post<void>(
      this.url,
      comercio
    );
  }
}

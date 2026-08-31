import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Cidade } from '@domain/cidade';
import { Observable } from 'rxjs';
import { environment } from '../app/environments/environment';

@Injectable()
export class ProjetoService {

  private readonly urlCidades =
    `${environment.apiUrl}${environment.urlCidades}`;

  constructor(private http: HttpClient) {}

  /**
   * Recupera a lista de cidades.
   */
  pesquisarCidades(): Observable<Cidade[]> {
    return this.http.get<Cidade[]>(this.urlCidades);
  }

  /**
   * Exclui a cidade informada.
   */
  excluir(cidade: Cidade): Observable<void> {
    return this.http.delete<void>(
      `${this.urlCidades}/${cidade.id}`
    );
  }

  /**
   * Cadastra ou altera uma cidade.
   */
  salvar(cidade: Cidade): Observable<void> {
    if (cidade.id !== undefined && cidade.id !== null) {
      return this.http.put<void>(
        this.urlCidades,
        cidade
      );
    }

    return this.http.post<void>(
      this.urlCidades,
      cidade
    );
  }
}

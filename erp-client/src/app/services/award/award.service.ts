import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { MessageService } from '../message/message.service';
import { Award } from '../../models/award.model';

@Injectable({ providedIn: 'root' })
export class AwardService {
  private headers: HttpHeaders = null;
  private endpointBase: string = null;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private httpClient: HttpClient, private messageService: MessageService) {}

  /** Log a AwardService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`AwardService: ${message}`);
  }
  
  /**
   * Handle an Http operation that fails, and let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
   
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead
   
      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);
   
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

	getAwardById(id: number): Observable<Award> {
		return this.httpClient.get<Award>(`/api/awards/${id}`);
	}

	getAllAwards(): Observable<Award[]> {
		return this.httpClient.get<Award[]>(`/api/awards`);
  }
  
  createAward(award: Award): Observable<Award> {
    console.log('creating award');
    return this.httpClient.post<Award>(`/api/awards`, award);
  }

}



import { Injectable } from '@angular/core';
import { PreferencesReq } from '../entities/preferences-req';
import { HttpClient } from '@angular/common/http';
import { ProcessId } from '../entities/process-id';
import { Observable } from 'rxjs';
import { Ingredients } from '../entities/ingredients';

@Injectable({
  providedIn: 'root'
})
export class MainService {

  private baseUri: string = 'http://lva924-server3.ec.tuwien.ac.at:8082/';

  constructor(
    private http: HttpClient,
  ) { }

  sendPreferences(preferencesReq: PreferencesReq) : Observable<ProcessId> {
    return this.http.post<ProcessId>(this.baseUri+'preferences', preferencesReq);
  }

  checkRecommenderSystem(processId: String) : Observable<Ingredients> {
    return this.http.get<Ingredients>(this.baseUri+'shampoo-details?processId='+processId);
  }
}

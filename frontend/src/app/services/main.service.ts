import { Injectable } from '@angular/core';
import { PreferencesReq } from '../entities/preferences-req';
import { HttpClient } from '@angular/common/http';
import { ProcessId } from '../entities/process-id';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MainService {

  private baseUri: string = 'shampoome.com';

  constructor(
    private http: HttpClient,
  ) { }

  sendPreferences(preferencesReq: PreferencesReq) : Observable<ProcessId> {
    return this.http.post<ProcessId>(this.baseUri, preferencesReq);
  }
}

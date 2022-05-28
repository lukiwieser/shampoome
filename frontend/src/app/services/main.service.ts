import { Injectable } from '@angular/core';
import { PreferencesReq } from '../entities/preferences-req';
import { HttpClient } from '@angular/common/http';
import { ProcessId } from '../entities/process-id';
import { Observable } from 'rxjs';
import { OrderReq } from '../entities/order-req';
import { ShampooDetails } from '../entities/shampoo-details';

@Injectable({
  providedIn: 'root'
})
export class MainService {

  // private baseUri: string = 'http://lva924-server3.ec.tuwien.ac.at:8082/';
  private baseUri: string = 'http://localhost:8080/';

  constructor(
    private http: HttpClient,
  ) { }

  sendPreferences(preferencesReq: PreferencesReq) : Observable<ProcessId> {
    return this.http.post<ProcessId>(this.baseUri+'preferences', preferencesReq);
  }

  checkRecommenderSystem(processId: String) : Observable<ShampooDetails> {
    return this.http.get<ShampooDetails>(this.baseUri+'shampoo-details?processId='+processId);

    // TODO: just for mocking the API remove later
    // return this.http.get<ShampooDetails>(this.baseUri+'shampoo-details-null');
  }

  placeOrder(orderReq: OrderReq) : Observable<any> {
    return this.http.post<any>(this.baseUri+'order', orderReq);
  }
}

import { Injectable } from '@angular/core';
import { BottleSize, DeliveryStatus } from '../components/entities/enums';

@Injectable({
  providedIn: 'root'
})
export class StringifyService {

  constructor() { }

  bottleSizeAsString(bottleSize : BottleSize):  String { 
    switch (bottleSize) {
      case "L": return "Large 1000ml";
      case "M": return "Medium 250ml"; 
      case "S": return "Small 100ml"; 
      default: return "";
    }
  }

  statusAsString(status: DeliveryStatus): String {
    switch (status) {
      case "order_placed": return "is beeing processed";
      case "on_the_way": return "is one the way"; 
      case "delivered": return "was delivered"; 
      default: return "";
    }
  }
}

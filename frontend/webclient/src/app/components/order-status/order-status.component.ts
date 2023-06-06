import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { MainService } from 'src/app/services/main.service';
import { StringifyService } from 'src/app/services/stringify.service';
import { Order } from '../../entities/order';

@Component({
  selector: 'app-order-status',
  templateUrl: './order-status.component.html',
  styleUrls: ['./order-status.component.scss']
})
export class OrderStatusComponent implements OnInit {
  orderId! : String | null;
  order!: Order;

  constructor(
    private titleService: Title,
    private route: ActivatedRoute,
    private mainService: MainService,
    public ss: StringifyService
  ) { }

  ngOnInit(): void {
    this.titleService.setTitle("Order Status | ShampooMe");
    this.route.paramMap.subscribe(params => {
      this.orderId = params.get("orderId");
      this.mainService.getOrderStatus(this.orderId ? this.orderId : "").subscribe(res => {
        this.order = res;
      });
    })
  }

  get priceReduced(): Number {
    if (!this.order) {
      return 0;
    }
    return Number(this.order.price) * 0.8;
  }

}

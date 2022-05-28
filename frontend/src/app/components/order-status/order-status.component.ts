import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
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
    public ss: StringifyService
  ) { }

  ngOnInit(): void {
    this.titleService.setTitle("Order Status | ShampooMe");
    this.route.paramMap.subscribe(params => {
      this.orderId = params.get("orderId");
      this.order = new Order(
        "Luki",
        "11223344",
        "Treeway 11 \n Vienna \n Austria",
        70,
        "S",
        "very nice stuff",
        " distilled water, decyl glucoside, glycerine, guar gum, coco betaine, ginger, goji berriescoconut oil, almonds, aloe vera, olive oil, apple cider vinegar, coconut oil, turmeric, grapefruit",
        false,
        "on_the_way",
        "11a2342fcf352gg751"
      );
    })
  }

  get priceReduced(): Number {
    if (!this.order) {
      return 0;
    }
    return Number(this.order.price) * 0.8;
  }

}

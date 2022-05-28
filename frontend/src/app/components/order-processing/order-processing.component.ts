import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-order-processing',
  templateUrl: './order-processing.component.html',
  styleUrls: ['./order-processing.component.scss']
})
export class OrderProcessingComponent implements OnInit {
  processId! : string | null;
  orderId!: string;
  estimatedDeliveryTime: Number = 26; 

  constructor(
    private titleService: Title,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.titleService.setTitle("Processing Order | ShampooMe");
    this.route.paramMap.subscribe(params => {
      this.processId = params.get("processId");

      this.orderId = "asdf557";
    });
  }

  get orderLink() : string {
    const domainAndPort = location.origin;
    return domainAndPort + "/order-status/" + this.orderId;
  }
}

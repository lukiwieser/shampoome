import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { MainService } from 'src/app/services/main.service';

@Component({
  selector: 'app-order-processing',
  templateUrl: './order-processing.component.html',
  styleUrls: ['./order-processing.component.scss']
})
export class OrderProcessingComponent implements OnInit {
  processId! : string | null;
  orderId!: string;
  estimatedDeliveryTime: Number = 26; 
  interval: NodeJS.Timeout | undefined;

  constructor(
    private titleService: Title,
    private mainService: MainService,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.titleService.setTitle("Processing Order | ShampooMe");
    this.route.paramMap.subscribe(params => {
      this.processId = params.get("processId");
      this.orderId ? null : this.periodicCheck();
    });
  }

  get orderLink() : string {
    const domainAndPort = location.origin;
    return domainAndPort + "/order-status/" + this.orderId;
  }

  periodicCheck(): void {
    this.interval = setInterval(() => {
      this.checkOrderLink();
    }, 2000);
  }

  checkOrderLink(): void {
    if(this.processId===null) {
      return 
    }
    this.mainService.checkOrderLink(this.processId).subscribe(res => {
      if(res ? true : false) {
        this.orderId = res.orderId;
        clearTimeout(this.interval)
      }
    });
  }
}

import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss']
})
export class OrderComponent implements OnInit {
  processId! : string | null;

  constructor(
    private route: ActivatedRoute,
    private titleService: Title
    ) { }

  ngOnInit(): void {
    this.titleService.setTitle("Order | ShampooMe");
    this.route.paramMap.subscribe(params => {
      this.processId = params.get("processId");
    })
  }
}

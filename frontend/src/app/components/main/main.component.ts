import { Component, OnInit } from '@angular/core';
import {Title} from "@angular/platform-browser";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

  constructor(
    private titleService: Title
  ) {
    this.titleService.setTitle("ShampooMe")
  }

  ngOnInit(): void {
  }

}

import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { MainService } from 'src/app/services/main.service';
import { Router } from '@angular/router';
import { Ingredients } from 'src/app/entities/ingredients';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss']
})
export class OrderComponent implements OnInit {
  processId! : string | null;
  ingredients!: Ingredients | null;

  constructor(
    private route: ActivatedRoute,
    private titleService: Title,
    private mainService: MainService,
    private router: Router
    ) { }

  ngOnInit(): void {
    this.titleService.setTitle("Order | ShampooMe");
    this.route.paramMap.subscribe(params => {
      this.processId = params.get("processId");
      this.periodicCheck();
    })
  }

  periodicCheck(): void {
    let ingredients = new Ingredients(null, null, null, null, null);
    const reqInterval = setInterval(() => {
        ingredients = this.checkRecommenderSystem();
    }, 2000);
    ingredients.nickName ? () => {
      clearInterval(reqInterval);
      this.ingredients = ingredients;
    } : null;
  }

  checkRecommenderSystem(): Ingredients {
    let ingredients = new Ingredients(null, null, null, null, null)
    if(this.processId===null) {
      return ingredients
    }
    this.mainService.checkRecommenderSystem(this.processId).subscribe(ingredientsRes => {
      ingredientsRes.nickName ? () => { ingredients = ingredientsRes } : null;
    });
    return ingredients
  }
}

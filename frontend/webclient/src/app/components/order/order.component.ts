import { Component, OnInit } from '@angular/core';
import { AbstractControl, UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { MainService } from 'src/app/services/main.service';
import { Router } from '@angular/router';
import { StringifyService } from 'src/app/services/stringify.service';
import { ShampooDetails } from '../../entities/shampoo-details';
import { OrderReq } from 'src/app/entities/order-req';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss']
})
export class OrderComponent implements OnInit {
  form: UntypedFormGroup;
  formSubmitted : Boolean = false;
  processId! : string | null;
  shampooDetails! : ShampooDetails;
  interval: NodeJS.Timeout | undefined;

  constructor(
    private route: ActivatedRoute,
    private titleService: Title,
    private mainService: MainService,
    private router: Router,
    private formBuilder: UntypedFormBuilder,
    public ss: StringifyService
    ) { 
      this.titleService.setTitle("Order | ShampooMe");
      this.form = this.formBuilder.group({
        matriculationNumber: ['',[Validators.required, Validators.minLength(8), Validators.maxLength(8)]],
        shippingAddress: ['',[Validators.required]]
      });
    }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.processId = params.get("processId");
      console.log(this.shampooDetails);
      this.shampooDetails ? null : this.periodicCheck();
    })
  }

  get matriculationNumber():  AbstractControl | null { return this.form.get('matriculationNumber'); }
  get shippingAddress():  AbstractControl | null { return this.form.get('shippingAddress'); }

  submit() {
    this.formSubmitted = true;
    if(this.form.invalid) {
      return;
    }
    const orderReq = new OrderReq(
      this.processId ? this.processId : "",
      this.shippingAddress?.value,
      this.matriculationNumber?.value,
    );

    this.mainService.placeOrder(orderReq).subscribe(res => {
      this.router.navigate([`../order-processing/${this.processId}`]);
    });
  }

  periodicCheck(): void {
    this.interval = setInterval(() => {
      this.checkRecommenderSystem();
    }, 2000);
  }

  checkRecommenderSystem(): void {
    if(this.processId===null) {
      return 
    }
    this.mainService.checkRecommenderSystem(this.processId).subscribe(shampooDetailsRes => {
      if(shampooDetailsRes ? true : false) {
        this.shampooDetails = new ShampooDetails(
          shampooDetailsRes.nickName,
          shampooDetailsRes.price,
          shampooDetailsRes.bottleSize,
          shampooDetailsRes.description,
          shampooDetailsRes.ingredients,
        )
        clearTimeout(this.interval)
      }
    });
  }
}

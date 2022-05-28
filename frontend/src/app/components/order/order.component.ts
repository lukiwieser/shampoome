import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { StringifyService } from 'src/app/services/stringify.service';
import { ShampooDetails } from '../entities/shampoo-details';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss']
})
export class OrderComponent implements OnInit {
  form: FormGroup;
  formSubmitted : Boolean = false;
  processId! : string | null;
  shampooDetails! : ShampooDetails;

  constructor(
    private route: ActivatedRoute,
    private titleService: Title,
    private formBuilder: FormBuilder,
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
    })

    this.shampooDetails = new ShampooDetails(
      "Luki",
      74,
      "S",
      "ist a very nice shampoo",
      "distilled water, decyl glucoside, glycerine, guar gum, coco betaine, ginger, goji berriescoconut oil, almonds, aloe vera, olive oil, apple cider vinegar, coconut oil, turmeric, grapefruit"
    );
  }

  get matriculationNumber():  AbstractControl | null { return this.form.get('matriculationNumber'); }
  get shippingAddress():  AbstractControl | null { return this.form.get('shippingAddress'); }

  submit() {
    this.formSubmitted = true;
    if(this.form.invalid) {
      return;
    }

    console.log(this.shippingAddress?.value)
  }
}

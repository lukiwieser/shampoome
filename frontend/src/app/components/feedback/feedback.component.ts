import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.component.html',
  styleUrls: ['./feedback.component.scss']
})
export class FeedbackComponent implements OnInit {
  processId! : String | null;
  form: FormGroup;
  formSubmitted : Boolean = false;
  formSubmittedSuccessfully : Boolean = false

  constructor(
    private titleService: Title,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
  ) {
    this.titleService.setTitle("Feedback | ShampooMe");
    this.form = this.formBuilder.group({
      overallSatisfaction: ['',[Validators.required]],
      priceSatisfaction: ['',[Validators.required]],
      comments: ['',[]]
    });
  }

  get overallSatisfaction():  AbstractControl | null { return this.form.get('overallSatisfaction'); }
  get priceSatisfaction():  AbstractControl | null { return this.form.get('priceSatisfaction'); }
  get comments():  AbstractControl | null { return this.form.get('comments'); }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.processId = params.get("processId");
    })
  }

  submit() {
    this.formSubmitted = true;
    if(this.form.invalid) {
      return;
    }

    console.log(this.overallSatisfaction?.value);
    console.log(this.priceSatisfaction?.value);
    console.log(this.comments?.value);

    this.formSubmittedSuccessfully = true;
  }
}

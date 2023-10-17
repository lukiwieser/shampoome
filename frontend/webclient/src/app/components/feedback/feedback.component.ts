import { Component, OnInit } from '@angular/core';
import { AbstractControl, UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { FeedbackReq } from 'src/app/entities/feedback-req';
import { MainService } from 'src/app/services/main.service';

@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.component.html',
  styleUrls: ['./feedback.component.scss']
})
export class FeedbackComponent implements OnInit {
  processId! : String | null;
  form: UntypedFormGroup;
  formSubmitted : Boolean = false;
  formSubmittedSuccessfully : Boolean = false

  constructor(
    private titleService: Title,
    private formBuilder: UntypedFormBuilder,
    private mainService: MainService,
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

    const feedbackReq = new FeedbackReq(
      this.processId ? this.processId : "",
      this.overallSatisfaction?.value,
      this.priceSatisfaction?.value,
      this.comments?.value,
    )
    this.mainService.postFeeback(feedbackReq).subscribe(res => {
    });

    this.formSubmittedSuccessfully = true;
  }
}

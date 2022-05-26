import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-quiz',
  templateUrl: './quiz.component.html',
  styleUrls: ['./quiz.component.scss']
})
export class QuizComponent implements OnInit {
  form: FormGroup;
  formSubmitted : Boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private titleSerivce: Title
  ) {
    this.titleSerivce.setTitle("Hair Quiz | ShampooMe");
    this.form = this.formBuilder.group({
      age: ['',[Validators.required]],
      hairType: ['',[Validators.required]],
      scalp: ['',[Validators.required]],
      splitEnds: ['',[Validators.required]],
      dandruff: ['',[Validators.required]],
      hairLossMedium: ['',[Validators.required]],
      hairLossStrong: ['',[Validators.required]],
      thinHair: ['',[Validators.required]],
      name: ['',[Validators.required,Validators.minLength(1)]],
    });
  }

  ngOnInit(): void {
  }

  get age():  AbstractControl | null { return this.form.get('age'); }
  get hairType():  AbstractControl | null { return this.form.get('hairType'); }
  get scalp():  AbstractControl | null { return this.form.get('scalp'); }
  get splitEnds():  AbstractControl | null { return this.form.get('splitEnds'); }
  get dandruff():  AbstractControl | null { return this.form.get('dandruff'); }
  get hairLossMedium():  AbstractControl | null { return this.form.get('hairLossMedium'); }
  get hairLossStrong():  AbstractControl | null { return this.form.get('hairLossStrong'); }
  get thinHair():  AbstractControl | null { return this.form.get('thinHair'); }
  get name():  AbstractControl | null { return this.form.get('name'); }

  submit() {
    this.formSubmitted = true;
    if(this.form.invalid) {
      return;
    }
  }

}

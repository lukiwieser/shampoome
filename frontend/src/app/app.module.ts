import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { QuizComponent } from './components/quiz/quiz.component';
import { MainComponent } from './components/main/main.component';
import { OrderComponent } from './components/order/order.component';
import { OrderProcessingComponent } from './components/order-processing/order-processing.component';
import { FeedbackComponent } from './components/feedback/feedback.component';
import { OrderStatusComponent } from './components/order-status/order-status.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { HeaderComponent } from './components/header/header.component';

@NgModule({
  declarations: [
    AppComponent,
    QuizComponent,
    MainComponent,
    OrderComponent,
    OrderProcessingComponent,
    FeedbackComponent,
    OrderStatusComponent,
    PageNotFoundComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

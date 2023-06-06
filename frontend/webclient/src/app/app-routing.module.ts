import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FeedbackComponent } from './components/feedback/feedback.component';
import { MainComponent } from './components/main/main.component';
import { OrderProcessingComponent } from './components/order-processing/order-processing.component';
import { OrderStatusComponent } from './components/order-status/order-status.component';
import { OrderComponent } from './components/order/order.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { QuizComponent } from './components/quiz/quiz.component';

const routes: Routes = [
  {path: "order/:processId", component: OrderComponent},
  {path: "order-processing/:processId", component: OrderProcessingComponent},
  {path: "order-status/:orderId", component: OrderStatusComponent},
  {path: "feedback/:processId", component: FeedbackComponent},
  {path: "", component: MainComponent},
  {path: "quiz", component: QuizComponent},
  {path: "**", component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

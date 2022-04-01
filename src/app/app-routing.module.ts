import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddTopicComponent } from './pages/topic/add-topic/add-topic.component';
import { AllTopicComponent } from './pages/topic/all-topic/all-topic.component';

const routes: Routes = [
  { path: 'topic/add', component: AddTopicComponent },
  { path: 'topic/all', component: AllTopicComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

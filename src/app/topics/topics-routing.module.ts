import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddTopicComponent } from './add-topic/add-topic.component';
import { AllTopicComponent } from './all-topic/all-topic.component';
import { AuthGuard } from '../guard/auth.guard';
import { TopicsResolver } from './topics.resolver';

const topicRoutes: Routes = [
  { path: 'add', component: AddTopicComponent, canActivate: [AuthGuard] },
  {
    path: 'all',
    component: AllTopicComponent,
    canActivate: [AuthGuard],
    resolve: { topics: TopicsResolver },
  },
];

@NgModule({
  imports: [RouterModule.forChild(topicRoutes)],
  exports: [RouterModule],
})
export class TopicsRoutingModule {}

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddTopicComponent } from './add-topic/add-topic.component';
import { AllTopicComponent } from './all-topic/all-topic.component';
import { AuthGuard } from '../guard/auth.guard';
import { TopicsResolver } from './topics.resolver';
import { TopicDetailsComponent } from './topic-details/topic-details.component';

const topicRoutes: Routes = [
  { path: 'add', component: AddTopicComponent, canActivate: [AuthGuard] },
  { path: 'all', redirectTo: 'all/page/1' },
  {
    path: 'all/page/:id',
    component: AllTopicComponent,
    canActivate: [AuthGuard],
    resolve: { topics: TopicsResolver },
  },
  {
    path: ':id',
    component: TopicDetailsComponent,
    resolve: { topics: TopicsResolver },
  },
];

@NgModule({
  imports: [RouterModule.forChild(topicRoutes)],

  exports: [RouterModule],
})
export class TopicsRoutingModule {}

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddTopicComponent } from './pages/add-topic/add-topic.component';

const routes: Routes = [{ path: 'topic/add', component: AddTopicComponent }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

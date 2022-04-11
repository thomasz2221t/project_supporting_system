import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddTopicComponent } from './pages/topic/add-topic/add-topic.component';
import { AllTopicComponent } from './pages/topic/all-topic/all-topic.component';
import { HomePageComponent } from './pages/home/home-page/home-page.component';
import { AuthGuard } from './guard/auth.guard';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomePageComponent },
  { path: 'topic/add', component: AddTopicComponent },
  { path: 'topic/all', component: AllTopicComponent, canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

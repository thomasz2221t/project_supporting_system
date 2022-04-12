import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopicsRoutingModule } from './topics-routing.module';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { HttpClientModule } from '@angular/common/http';
import { MatMenuModule } from '@angular/material/menu';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { AddTopicComponent } from './add-topic/add-topic.component';
import { AllTopicComponent } from './all-topic/all-topic.component';
import { TopicService } from './services/topic.service';
import { LongTextPipe } from './pipes/long-text.pipe';
import { MatFormFieldModule } from '@angular/material/form-field';

@NgModule({
  declarations: [AddTopicComponent, AllTopicComponent, LongTextPipe],
  imports: [
    CommonModule,
    TopicsRoutingModule,
    MatInputModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatButtonModule,
    HttpClientModule,
    MatMenuModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
  ],
  exports: [AddTopicComponent, AllTopicComponent],
  providers: [TopicService],
})
export class TopicsModule {}

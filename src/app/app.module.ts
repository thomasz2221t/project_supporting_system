import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AddTopicComponent } from './pages/topic/add-topic/add-topic.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MenuComponent } from './ui/menu/menu.component';
import { MatMenuModule } from '@angular/material/menu';
import { MatDialogModule } from '@angular/material/dialog';
import { OkDialogComponent } from './ui/dialog/ok-dialog/ok-dialog.component';
import { ErrorDialogComponent } from './ui/dialog/error-dialog/error-dialog.component';
import { AllTopicComponent } from './pages/topic/all-topic/all-topic.component';
import { MatTableModule } from '@angular/material/table';
import { LongTextPipe } from './pipes/long-text.pipe';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { CollapseElement } from './ui/menu/menu.component';
import { HomePageComponent } from './pages/home/home-page/home-page.component';
import { ProgressSpinnerComponent } from './ui/dialog/progress-spinner/progress-spinner.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@NgModule({
  declarations: [
    AppComponent,
    AddTopicComponent,
    MenuComponent,
    OkDialogComponent,
    ErrorDialogComponent,
    AllTopicComponent,
    LongTextPipe,
    CollapseElement,
    HomePageComponent,
    ProgressSpinnerComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    ReactiveFormsModule,
    MatButtonModule,
    HttpClientModule,
    MatMenuModule,
    MatDialogModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatProgressSpinnerModule,
  ],
  exports: [CollapseElement],
  providers: [],
  bootstrap: [AppComponent],
  entryComponents: [OkDialogComponent],
})
export class AppModule {}

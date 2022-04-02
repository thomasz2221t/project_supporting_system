import { Component, OnInit, ViewChild } from '@angular/core';
import { TopicService } from '../../../service/topic.service';
import { Topic } from 'src/app/model/topic';
import {
  MatDialog,
  MatDialogConfig,
  MatDialogRef,
} from '@angular/material/dialog';
import { ErrorDialogComponent } from 'src/app/ui/dialog/error-dialog/error-dialog.component';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { ProgressSpinnerComponent } from '../../../ui/dialog/progress-spinner/progress-spinner.component';

@Component({
  selector: 'app-all-topic',
  templateUrl: './all-topic.component.html',
  styleUrls: ['./all-topic.component.scss'],
})
export class AllTopicComponent implements OnInit {
  topicList: Topic[] = [];
  displayedColumns: string[] = ['topicName', 'description'];

  @ViewChild(MatPaginator) paginator: MatPaginator | null;
  @ViewChild(MatSort) sort: MatSort | null;
  dataSource = new MatTableDataSource<Topic>(this.topicList);
  constructor(private topicService: TopicService, public dialog: MatDialog) {
    this.paginator = null;
    this.sort = null;
  }

  fetchData() {
    const dialogRef = this.showLoadingSpinner();
    this.topicService.getAllTopics().subscribe({
      next: (res) => {
        dialogRef.close();
        this.topicList = res;
        this.updateDataSource();
      },
      error: (err) => {
        dialogRef.close();
        this.openErrorDialog(err);
      },
    });
  }

  showLoadingSpinner(): MatDialogRef<ProgressSpinnerComponent> {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.closeOnNavigation = true;
    dialogConfig.panelClass = 'noBackgroundDialog';
    return this.dialog.open(ProgressSpinnerComponent, dialogConfig);
  }

  rowClicked(row: any) {
    console.log(row);
  }

  updateDataSource() {
    this.dataSource = new MatTableDataSource<Topic>(this.topicList);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  openErrorDialog(errorMessage: string) {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = false;
    dialogConfig.autoFocus = true;
    dialogConfig.closeOnNavigation = true;
    dialogConfig.data = {
      errorMessage: errorMessage,
    };

    this.dialog.open(ErrorDialogComponent, dialogConfig);
  }

  ngOnInit(): void {
    this.fetchData();
  }
}

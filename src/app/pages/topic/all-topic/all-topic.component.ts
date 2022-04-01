import { Component, OnInit, ViewChild } from '@angular/core';
import { TopicService } from '../../../service/topic.service';
import { Topic } from 'src/app/model/topic';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ErrorDialogComponent } from 'src/app/ui/dialog/error-dialog/error-dialog.component';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';

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
    this.topicService.getAllTopics().subscribe({
      next: (res) => {
        this.topicList = res;
        this.updateDataSource();
      },
      error: (err) => this.openErrorDialog(err),
    });
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

    this.dialog.open(ErrorDialogComponent, {
      data: {
        errorMessage: errorMessage,
      },
    });
  }

  ngOnInit(): void {
    this.fetchData();
  }
}

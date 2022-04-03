import { Component, OnInit, ViewChild } from '@angular/core';
import { TopicService } from '../../../service/topic.service';
import { Topic } from 'src/app/model/topic';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { DialogService } from '../../../service/dialog.service';

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
  constructor(
    private topicService: TopicService,
    private dialogService: DialogService
  ) {
    this.paginator = null;
    this.sort = null;
  }

  fetchData() {
    const dialogRef = this.dialogService.showLoadingSpinner();
    this.topicService.getAllTopics().subscribe({
      next: (res) => {
        dialogRef.close();
        this.topicList = res;
        this.updateDataSource();
      },
      error: (err) => {
        dialogRef.close();
        this.dialogService.openErrorDialog(err);
      },
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

  ngOnInit(): void {
    this.fetchData();
  }
}

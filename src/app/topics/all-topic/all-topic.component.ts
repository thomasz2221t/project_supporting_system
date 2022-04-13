import { Component, OnInit, ViewChild } from '@angular/core';
import { Topic } from 'src/app/topics/model/topic';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { TopicService } from '../services/topic.service';
import { DialogService } from 'src/app/shared/services/dialog.service';
import { catchError, map, Observable, of } from 'rxjs';

@Component({
  selector: 'app-all-topic',
  templateUrl: './all-topic.component.html',
  styleUrls: ['./all-topic.component.scss'],
})
export class AllTopicComponent implements OnInit {
  displayedColumns: string[] = ['topicName', 'description'];

  dataSource$: Observable<MatTableDataSource<Topic>>;

  @ViewChild(MatPaginator) paginator: MatPaginator | null;
  @ViewChild(MatSort) sort: MatSort | null;
  constructor(private topicService: TopicService) {
    this.paginator = null;
    this.sort = null;
  }
  rowClicked(row: any) {
    console.log(row);
  }

  ngOnInit(): void {
    this.dataSource$ = this.topicService.getAllTopics().pipe(
      map((topics: Topic[]) => {
        const data = new MatTableDataSource(topics);
        data.paginator = this.paginator;
        data.sort = this.sort;
        return data;
      })
    );
  }
}

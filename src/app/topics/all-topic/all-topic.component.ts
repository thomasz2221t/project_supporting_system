import { Component, OnInit, ViewChild } from '@angular/core';
import { Topic } from 'src/app/topics/model/topic';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { map, Observable, shareReplay } from 'rxjs';
import { TopicsState } from '../store/reducers';
import { select, Store } from '@ngrx/store';
import { selectAllTopics } from '../store/topics.selectors';

@Component({
  selector: 'app-all-topic',
  templateUrl: './all-topic.component.html',
  styleUrls: ['./all-topic.component.scss'],
})
export class AllTopicComponent implements OnInit {
  displayedColumns: string[] = ['topicName', 'description'];

  dataSource$: Observable<Topic[]>;
  dataSourceObtained$: Observable<boolean>;

  @ViewChild(MatPaginator) paginator: MatPaginator | null;
  @ViewChild(MatSort) sort: MatSort | null;
  constructor(private store: Store<TopicsState>) {
    this.paginator = null;
    this.sort = null;
  }
  rowClicked(row: any) {
    console.log(row);
  }

  reload() {
    this.dataSource$ = this.store.pipe(select(selectAllTopics), shareReplay());
    this.dataSourceObtained$ = this.dataSource$.pipe(map((data) => !!data));
  }

  ngOnInit(): void {
    this.reload();
  }
}

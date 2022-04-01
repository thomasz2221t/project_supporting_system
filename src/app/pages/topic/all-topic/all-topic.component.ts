import { Component, OnInit } from '@angular/core';
import { TopicService } from '../../../service/topic.service';
import { Topic } from 'src/app/model/topic';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ErrorDialogComponent } from 'src/app/ui/dialog/error-dialog/error-dialog.component';

@Component({
  selector: 'app-all-topic',
  templateUrl: './all-topic.component.html',
  styleUrls: ['./all-topic.component.scss'],
})
export class AllTopicComponent implements OnInit {
  topicList: Topic[] = [];
  displayedColumns: string[] = ['topicName', 'description'];

  constructor(private topicService: TopicService, public dialog: MatDialog) {
    topicService.getAllTopics().subscribe({
      next: (res) => {
        this.topicList = res;
        console.log(this.topicList);
      },
      error: (err) => this.openErrorDialog(err),
    });
  }

  rowClicked(row: any) {
    console.log(row);
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

  ngOnInit(): void {}
}

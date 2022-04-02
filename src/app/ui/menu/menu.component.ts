import {
  Component,
  Directive,
  ElementRef,
  OnInit,
  QueryList,
  ViewChildren,
} from '@angular/core';
import { NavigationStart, Router } from '@angular/router';

@Directive({
  selector: '.collapse',
})
export class CollapseElement {}

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss'],
})
export class MenuComponent implements OnInit {
  @ViewChildren(CollapseElement, { read: ElementRef })
  collpsable: QueryList<ElementRef> | null;

  constructor(private router: Router) {
    this.collpsable = null;
    this.router.events.subscribe((event) => {
      if (!(event instanceof NavigationStart)) return;
      this.collpsable?.forEach((e) => {
        e.nativeElement.classList.remove('show');
      });
    });
  }

  isActive(base: string): boolean {
    return this.router.url.includes(`/${base}`);
  }

  ngOnInit(): void {}

  ngAfterViewInit() {}
}

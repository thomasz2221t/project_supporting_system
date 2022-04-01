import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'longText',
})
export class LongTextPipe implements PipeTransform {
  transform(value: any, length: number): any {
    if (typeof value != 'string' || !(value.length > length)) {
      return value;
    }
    return value.slice(0, length - 3) + '...';
  }
}

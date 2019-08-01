import { Pipe, PipeTransform } from '@angular/core';

/*
 * Converts a string containing a time representation
 * into a human-friendly 12-hour time format
 * Usage:
 *   value | time:string
 * Example:
 *   {{ 20:00 | time }}
 *   formats to: 8 PM
*/
@Pipe({name: 'time'})
export class ConvertTimePipe implements PipeTransform {
  transform(value: string): string {
    if (!value) return;

    const timeArr = value.split(':');
    const meridiem = timeArr[0] > '11' ? 'PM' : 'AM';
    return `${(Number(timeArr[0]) + 11) % 12 + 1}:${timeArr[1]} ${meridiem}`;
  }
}
import { Component, Input, OnInit } from '@angular/core';
import { SnackbarService } from 'src/app/services/snackbar/snackbar.service';

@Component({
  selector: 'vertical-bar-chart',
  templateUrl: './vertical-bar-chart.component.html',
  styleUrls: ['./vertical-bar-chart.component.scss']
})
export class VerticalBarChartComponent implements OnInit {

  /**
   * All of these can be overridden as inputs in the template.
   *   data format: [{name, value}, {name, value}, ...]
   */
  @Input() data: any[] = [];                    // the data passed into the component
  @Input() view: any[] = [1000, 200];           // [width, height]
  @Input() showAxisLabels: boolean = true;      // controls whether axis labels show
  @Input() showAxes: boolean = true;            // controls whether axes AND their labels show
  @Input() showLegend: boolean = true;          // controls whether a legend is displayed
  @Input() axisLabels: string[] = ['X','Y'];    // set the axis labels

  valueLabels: boolean;
  showGridlines: boolean;
  showAll: boolean = true;
  filteredData: any[];

  constructor(private _snackBar: SnackbarService) {
    this.valueLabels = true;
    this.showGridlines = true;
  }

  ngOnInit() {
    this.filteredData = this.nonZeroData();
  }

  /**
   * Checks the data for vertical bar chart format validity
   * @param data any[] - array of {name, value} objects
   */
  isValidData(data: any[]): boolean {
    if (data.length < 1)
      return false;

    let keys;
    let invalid = data.filter( d => {
      keys = Object.keys(d);
      return !d.name || !d.value || keys.length > 2
    });

    if (invalid.length > 0)
      return false;
    return true;
  }

  toggleValueLabels() {
    this.valueLabels = !this.valueLabels;
  }

  toggleGridlines() {
    this.showGridlines = !this.showGridlines;
  }

  toggleShowAll() {
    this.showAll = !this.showAll;
  }

  nonZeroData() {
    return this.data.filter(d => d.value > 0);
  }

  onSelect(event) {

  }
}

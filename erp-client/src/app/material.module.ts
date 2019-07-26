import { NgModule } from '@angular/core';

import {
  MatTableModule,
  MatPaginatorModule,
  MatSortModule,
  MatFormFieldModule,
  MatInputModule
} from '@angular/material';

import { NoopAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  imports: [
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatFormFieldModule,
    MatInputModule,
    NoopAnimationsModule
  ],
  exports: [
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatFormFieldModule,
    MatInputModule,
    NoopAnimationsModule
  ]
})
export class MaterialModule {}
import { NgModule } from '@angular/core';

import {
  MatTableModule,
  MatPaginatorModule,
  MatSortModule,
  MatFormFieldModule,
  MatInputModule,
  MatCheckboxModule,
  MatTabsModule
} from '@angular/material';

import {MatButtonModule} from '@angular/material/button';

import { NoopAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  imports: [
    MatButtonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatFormFieldModule,
    MatInputModule,
    MatCheckboxModule,
    MatTabsModule,
    NoopAnimationsModule
  ],
  exports: [
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatFormFieldModule,
    MatInputModule,
    MatCheckboxModule,
    MatTabsModule,
    MatButtonModule,
    NoopAnimationsModule
  ]
})
export class MaterialModule {}
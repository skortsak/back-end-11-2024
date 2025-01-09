import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { CheckoutService } from '../../services/checkout.service';
import { Page } from '../../models/page';
import { Checkout } from '../../models/checkout';

@Component({
  selector: 'app-checkouts',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss']
})
export class CheckoutComponent implements OnInit {
  displayedColumns: string[] = ['borrower', 'bookTitle', 'checkedOutDate', 'dueDate', 'returnedDate'];
  dataSource = new MatTableDataSource<Checkout>();

  totalElements = 0;
  pageSize = 10;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private checkoutService: CheckoutService) {}

  ngOnInit(): void {
    this.loadCheckouts();
  }

  loadCheckouts(): void {
    const pageIndex = this.paginator?.pageIndex || 0;
    const pageSize = this.paginator?.pageSize || this.pageSize;
    const sortDirection = this.sort?.direction || 'asc';
    const sortActive = this.sort?.active || '';

    const filter: any = {
      pageIndex,
      pageSize,
      sort: sortActive,
      direction: sortDirection
    };

    this.checkoutService.getCheckouts(filter).subscribe((page: Page<Checkout>) => {
      this.dataSource.data = page.content;
      this.totalElements = page.totalElements;
    });
  }

  onPaginatorChange(): void {
    this.loadCheckouts();
  }

  onSortChange(): void {
    this.loadCheckouts();
  }
}

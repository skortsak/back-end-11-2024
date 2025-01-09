import { Component, OnInit, ViewChild } from '@angular/core';
import { BookService } from '../../services/book.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Page } from '../../models/page';
import { Book } from '../../models/book';

@Component({
  selector: 'app-books-list',
  templateUrl: './books-list.component.html',
  styleUrls: ['./books-list.component.scss']
})
export class BooksListComponent implements OnInit {
  displayedColumns: string[] = ['title', 'author', 'genre', 'year', 'status'];
  dataSource = new MatTableDataSource<Book>();

  totalElements = 0;
  pageSize = 10;
  searchText: string = '';

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private bookService: BookService) {}

  ngOnInit(): void {
    this.loadBooks();
  }

  loadBooks(): void {
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

    this.bookService.getBooks(filter).subscribe((page: Page<Book>) => {
      this.dataSource.data = page.content;
      this.totalElements = page.totalElements;
    });
  }

  onPaginatorChange(): void {
    this.loadBooks();
  }

  onSortChange(): void {
    this.loadBooks();
  }

  onSearch(event: any): void {
    this.searchText = event.target.value;
    this.loadBooks();
  }
}

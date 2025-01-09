import { Component, OnInit } from '@angular/core';
import { BookService } from '../../services/book.service';
import { Book } from '../../models/book';
import { Observable } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { map, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.scss']
})
export class BookDetailComponent implements OnInit {
  book$!: Observable<Book>;

  constructor(
    private route: ActivatedRoute,
    private bookService: BookService,
  ) {}

  ngOnInit(): void {
    this.book$ = this.route.params
      .pipe(
        map(params => params['id']),
        switchMap(id => this.bookService.getBook(id))
      );
  }

  getStatusColor(status: string): string {
    switch (status) {
      case 'AVAILABLE':
        return 'primary';
      case 'BORROWED':
        return 'warn';
      case 'RETURNED':
        return 'accent';
      case 'DAMAGED':
        return 'warn';
      case 'PROCESSING':
        return 'primary';
      default:
        return 'default';
    }
  }
}

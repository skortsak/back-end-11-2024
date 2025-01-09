import { Component } from '@angular/core';
import { ProductService } from '../services/product.service';
import { CartService } from '../services/cart.service';
import { Product } from '../models/Product';
import { CategoryService } from '../services/category.service';
import { Category } from '../models/Category';
import { TranslatePipe } from '@ngx-translate/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [TranslatePipe, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  // TODO: nool vasakule leheküljenumbrite ees ja nool paremale leheküljenumbrite järel
  // kui vajutada vasakule, siis vähendab leheküljenumbrit
  // kui vajutada paremale, siis suurendab leheküljenumbrit
  // disabled kui on esimesel või viimasel lehel
  products: Product[] = [];
  currentPage = 1;
  pageSize = 3;
  pages: number[] = [];
  totalElements = 0;
  categories: Category[] = [];

  // failide sidumiseks
  constructor(private productService: ProductService,
    private cartService: CartService,
    private categoryService: CategoryService
  ) {}

  // lehele tuleku käima minemise funktsioon
  ngOnInit(): void {
    this.loadProducts();
    this.loadCategories();
  }

  private loadProducts() {
    this.productService.getProducts(this.currentPage, this.pageSize).subscribe(response => {
      this.products = response.content;
      this.totalElements = response.totalElements;
      this.pages = [];
      for (let index = 1; index <= response.totalPages; index++) {
        this.pages.push(index);
      }
    });
  }

  private loadCategories() {
    this.categoryService.getCategories().subscribe(res => {
      this.categories = res;
    })
  }

  //TODO: getCategoryProducts tagastab mulle kõik tooted, mitte lehekülje kaupa
  filterByCategory(categoryId: number) {
    this.productService.getCategoryProducts(categoryId).subscribe(res => {
      this.products = res;
    })
  }

  addToCart(product: Product) {
    this.cartService.addToCart(product);
  }

//  fetchProducts() {
//    //this.products = ["Coca", "Fanta", "Sprite"];
//    this.http.get<any[]>("http://localhost:8080/products").subscribe(response => 
//      this.products = response)
//  }

  changePage(newPage: number) {
    this.currentPage = newPage;
    this.productService.getProducts(newPage, this.pageSize).subscribe(response => {
      this.products = response.content;
    });
  }

  changePageSize(newSize: number) {
    this.pageSize = newSize;
    this.productService.getProducts(this.currentPage, this.pageSize).subscribe(response => {
      this.products = response.content;
      this.pages = [];
      for (let index = 1; index <= response.totalPages; index++) {
        this.pages.push(index);
      }
    });
  }
}

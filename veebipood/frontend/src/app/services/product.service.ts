import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../models/Product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private url = "http://localhost:8080/products";

  constructor(private http: HttpClient) { }

  getProducts(pageNumber: number, pageSize: number): Observable<any> {
    return this.http.get<any>(this.url, {params: {page: pageNumber - 1, size: pageSize}});
  }

  getAllProducts(): Observable<any> {
    return this.http.get<any>("http://localhost:8080/all-products", 
      {headers: {"Authorization": "Bearer " + sessionStorage.getItem("token") || ""}});
  }

  getProduct(name: string): Observable<Product> {
    return this.http.get<Product>("http://localhost:8080/product/" + name);
  }

  addProduct(product: Product): Observable<Product[]> {
    return this.http.post<Product[]>(this.url, product,
      {headers: {"Authorization": "Bearer " + sessionStorage.getItem("token")}}
    );
  }

  deleteProduct(name: string): Observable<Product[]> {
    return this.http.delete<any[]>(this.url + "/" + name,
      {headers: {"Authorization": "Bearer " + sessionStorage.getItem("token")}}
    );
  }

  getCategoryProducts(categoryId: number): Observable<Product[]> {
    return this.http.get<Product[]>("http://localhost:8080/category-products",
      {params: {categoryId}});
  }

  editProduct(product: Product) {
    return this.http.put<any[]>(this.url, product,
      {headers: {"Authorization": "Bearer " + sessionStorage.getItem("token")}}
    );
  }
}

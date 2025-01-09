import { Injectable } from '@angular/core';
import { Product } from '../models/Product';
import { HttpClient } from '@angular/common/http';
import { CartProduct } from '../models/CartProduct';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cart: CartProduct[] = JSON.parse(localStorage.getItem("cart") || "[]");
  cartSumSubject = new BehaviorSubject(this.calculateTotal());
  // JSON.parse --> võtab jutumärgid ära.
  // localStorage-sse pannes ja võttes on alati jutumärgid ümber

  constructor(private http: HttpClient) { }

  saveOrder(personId: number) {
    return this.http.post("http://localhost:8080/orders", 
      {"cartRows": this.cart, "person": {"id": personId}});
  }

  private updateNavbarSumAndSaveToLS() {
    localStorage.setItem("cart", JSON.stringify(this.cart));
    this.cartSumSubject.next(this.calculateTotal());
  }

  addToCart(productClicked: Product) {
    const cartRow = this.cart.find(p => p.product.name === productClicked.name)
    if (cartRow != undefined) {
      cartRow.quantity++;
    } else {
      this.cart.push({"product": productClicked, "quantity": 1});
    }
    this.updateNavbarSumAndSaveToLS();
  }

  getCart() {
    return this.cart;
  }

  decreaseQuqantity(cartRow: any) {
    cartRow.quantity--;
    if (cartRow.quantity === 0) {
      const index = this.cart.indexOf(cartRow);
      this.cart.splice(index, 1);
    }
    this.updateNavbarSumAndSaveToLS();
  }

  increaseQuantity(cartRow: any) {
    cartRow.quantity++;
    this.updateNavbarSumAndSaveToLS();
  }

  remove(index: number) {
    this.cart.splice(index, 1);
    this.updateNavbarSumAndSaveToLS();
  }

  getParcelMachines(country: string) {
    //return this.http.get("http://localhost:8080/parcel-machines?country=" + country)
    return this.http.get("http://localhost:8080/parcel-machines",  {params: {country}})
  }

  calculateTotal() {
    let sum = 0;

    this.cart.forEach(cartProduct => {
      sum += cartProduct.product.price * cartProduct.quantity;
    });
    return sum;
  }
}

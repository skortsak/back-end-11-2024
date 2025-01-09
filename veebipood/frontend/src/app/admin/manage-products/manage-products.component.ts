import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../services/product.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-manage-products',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './manage-products.component.html',
  styleUrl: './manage-products.component.css'
})
export class ManageProductsComponent {
  products: any[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.productService.getAllProducts().subscribe(res =>
      this.products = res
    );
  }

  removeProduct(name: string) {
    this.productService.deleteProduct(name).subscribe(response => 
      this.products = response
    )
  }
}

import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Nutrients, Product } from '../../models/Product';
import { Category } from '../../models/Category';
import { Characteristic } from '../../models/Characteristic';
import { HttpClient } from '@angular/common/http';
import { ProductService } from '../../services/product.service';
import { CategoryService } from '../../services/category.service';

@Component({
  selector: 'app-add-product',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './add-product.component.html',
  styleUrl: './add-product.component.css'
})
export class AddProductComponent {
  message = "";
  categories: Category[] = [];

  constructor(private productService: ProductService,
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    this.categoryService.getCategories().subscribe(res => 
      this.categories = res
    )
  }

  onSubmit(form: NgForm) {
    const val = form.value;
    const product = new Product(0, val.name, val.price, val.active, val.image,
      new Nutrients(val.protein, val.carbohydrate, val.fat), 
      new Category(val.category, ""), 
      [new Characteristic(val.characteristic, "")]
    );
    console.log(JSON.stringify(product)) // body copy-paste'imiseks
    this.productService.addProduct(product).subscribe({
    next: () => {
      form.reset();
    },
    error: (res) => {
      this.message = res.error.name; // .name on meie pandud
    }
  });
  }
}

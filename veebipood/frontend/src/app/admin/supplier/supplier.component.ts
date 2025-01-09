import { Component } from '@angular/core';
import { SupplierService } from '../../services/supplier.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-supplier',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './supplier.component.html',
  styleUrl: './supplier.component.css'
})
export class SupplierComponent {
  search = "spring";
  storeProducts: any;
  escuelaProducts: any;
  books: any;

  constructor (private supplierService: SupplierService) {}

  ngOnInit(): void {
    this.supplierService.getSupplierProducts().subscribe(data => {
      this.storeProducts = data;
    })
    this.supplierService.getEscuelaProducts().subscribe(data => {
      this.escuelaProducts = data;
    })
    this.supplierService.getBooks(this.search).subscribe((data: any) => {
      this.books = data.books;
    })
  }

  searchBooks() {
    if (this.search.length >= 3) {
      this.supplierService.getBooks(this.search).subscribe((data: any) => {
        this.books = data.books;
      })
    }
  }

  parseImage(image: String) {
    return image.replaceAll('[\"','').replaceAll('\"]','');
  }
}

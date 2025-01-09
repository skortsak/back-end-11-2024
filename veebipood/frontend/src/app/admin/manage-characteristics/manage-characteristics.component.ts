import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CharacteristicService } from '../../services/characteristic.service';

@Component({
  selector: 'app-manage-characteristics',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './manage-characteristics.component.html',
  styleUrl: './manage-characteristics.component.css'
})
export class ManageCharacteristicsComponent {
  characteristics: any[] = [];
  newCharacteristic = "";

  // Dependency Injection -> Springis @Autowired
  constructor(private characteristicService: CharacteristicService) {}

  ngOnInit(): void {
    this.characteristicService.getCharacteristics().subscribe(res =>
      this.characteristics = res
    );
  }

  addCharacteristics() {
    this.characteristicService.addCharacteristics(this.newCharacteristic).subscribe(res =>
      this.characteristics = res
    );
  }


  removeCharacteristics(id: number) {
    this.characteristicService.removeCharacteristics(id).subscribe(res =>
      this.characteristics = res
    );
  }
}

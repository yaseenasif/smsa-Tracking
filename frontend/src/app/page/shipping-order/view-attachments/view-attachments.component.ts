import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { InternationalShippingService } from '../international/service/international-shipping.service';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-view-attachments',
  templateUrl: './view-attachments.component.html',
  styleUrls: ['./view-attachments.component.scss'],
  providers:[MessageService]
})
export class ViewAttachmentsComponent {
  items: MenuItem[] | undefined;
  fileMetaData!:any[];
  shipmentId:any;
  shipmentType:any;


  constructor(private internationalShippingService: InternationalShippingService,
    private route:ActivatedRoute){

  }

  ngOnInit() {
    this.shipmentType = +this.route.snapshot.paramMap.get('name')!;

    this.shipmentId = +this.route.snapshot.paramMap.get('id')!;

      this.items = [{ label: 'View Attachments'}];
      this.getFileMetaDataByDomesticShipment(this.shipmentId);
  }

  getFileMetaDataByDomesticShipment(id:number){
    this.internationalShippingService.getFileMetaDataByDomesticShipment(id).subscribe((res:any)=>{
      this.fileMetaData=res;
    },(error:any)=>{
      console.log(error);
      
    })
  }
 
}
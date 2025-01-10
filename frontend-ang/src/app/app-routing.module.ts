import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from './home/home.component';
import {DescriptionComponent} from './description/description.component';
import {InfoComponent} from './info/info.component';

const routes: Routes = [
  {path :"home", component : HomeComponent},
  {path :"description", component : DescriptionComponent},
  {path :"home", component : InfoComponent},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

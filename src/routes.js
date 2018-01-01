import { subComponent, testComponent} from "./components";

const routes = [
  { path: '', component: testComponent},
  { path: '/sub/:name_one', component: subComponent, props: true, name: 'subRoute'} //set props to true in order to set params as props
]

export default routes
